import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPostView, PostView } from '../post-view.model';
import { PostViewService } from '../service/post-view.service';
import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';

@Component({
  selector: 'jhi-post-view-update',
  templateUrl: './post-view-update.component.html',
})
export class PostViewUpdateComponent implements OnInit {
  isSaving = false;

  postsSharedCollection: IPost[] = [];

  editForm = this.fb.group({
    id: [],
    post: [],
  });

  constructor(
    protected postViewService: PostViewService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postView }) => {
      this.updateForm(postView);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postView = this.createFromForm();
    if (postView.id !== undefined) {
      this.subscribeToSaveResponse(this.postViewService.update(postView));
    } else {
      this.subscribeToSaveResponse(this.postViewService.create(postView));
    }
  }

  trackPostById(_index: number, item: IPost): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostView>>): void {
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

  protected updateForm(postView: IPostView): void {
    this.editForm.patchValue({
      id: postView.id,
      post: postView.post,
    });

    this.postsSharedCollection = this.postService.addPostToCollectionIfMissing(this.postsSharedCollection, postView.post);
  }

  protected loadRelationshipsOptions(): void {
    this.postService
      .query()
      .pipe(map((res: HttpResponse<IPost[]>) => res.body ?? []))
      .pipe(map((posts: IPost[]) => this.postService.addPostToCollectionIfMissing(posts, this.editForm.get('post')!.value)))
      .subscribe((posts: IPost[]) => (this.postsSharedCollection = posts));
  }

  protected createFromForm(): IPostView {
    return {
      ...new PostView(),
      id: this.editForm.get(['id'])!.value,
      post: this.editForm.get(['post'])!.value,
    };
  }
}
