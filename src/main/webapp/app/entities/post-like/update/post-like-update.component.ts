import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPostLike, PostLike } from '../post-like.model';
import { PostLikeService } from '../service/post-like.service';
import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';

@Component({
  selector: 'jhi-post-like-update',
  templateUrl: './post-like-update.component.html',
})
export class PostLikeUpdateComponent implements OnInit {
  isSaving = false;

  postsSharedCollection: IPost[] = [];

  editForm = this.fb.group({
    id: [],
    post: [],
  });

  constructor(
    protected postLikeService: PostLikeService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postLike }) => {
      this.updateForm(postLike);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postLike = this.createFromForm();
    if (postLike.id !== undefined) {
      this.subscribeToSaveResponse(this.postLikeService.update(postLike));
    } else {
      this.subscribeToSaveResponse(this.postLikeService.create(postLike));
    }
  }

  trackPostById(_index: number, item: IPost): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostLike>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(postLike: IPostLike): void {
    this.editForm.patchValue({
      id: postLike.id,
      post: postLike.post,
    });

    this.postsSharedCollection = this.postService.addPostToCollectionIfMissing(this.postsSharedCollection, postLike.post);
  }

  protected loadRelationshipsOptions(): void {
    this.postService
      .query()
      .pipe(map((res: HttpResponse<IPost[]>) => res.body ?? []))
      .pipe(map((posts: IPost[]) => this.postService.addPostToCollectionIfMissing(posts, this.editForm.get('post')!.value)))
      .subscribe((posts: IPost[]) => (this.postsSharedCollection = posts));
  }

  protected createFromForm(): IPostLike {
    return {
      ...new PostLike(),
      id: this.editForm.get(['id'])!.value,
      post: this.editForm.get(['post'])!.value,
    };
  }
}
