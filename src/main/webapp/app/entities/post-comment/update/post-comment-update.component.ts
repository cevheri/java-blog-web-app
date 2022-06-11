import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPostComment, PostComment } from '../post-comment.model';
import { PostCommentService } from '../service/post-comment.service';
import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';

@Component({
  selector: 'jhi-post-comment-update',
  templateUrl: './post-comment-update.component.html',
})
export class PostCommentUpdateComponent implements OnInit {
  isSaving = false;

  postsSharedCollection: IPost[] = [];

  editForm = this.fb.group({
    id: [],
    commentText: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(4000)]],
    post: [],
  });

  constructor(
    protected postCommentService: PostCommentService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postComment }) => {
      this.updateForm(postComment);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postComment = this.createFromForm();
    if (postComment.id !== undefined) {
      this.subscribeToSaveResponse(this.postCommentService.update(postComment));
    } else {
      this.subscribeToSaveResponse(this.postCommentService.create(postComment));
    }
  }

  trackPostById(_index: number, item: IPost): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostComment>>): void {
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

  protected updateForm(postComment: IPostComment): void {
    this.editForm.patchValue({
      id: postComment.id,
      commentText: postComment.commentText,
      post: postComment.post,
    });

    this.postsSharedCollection = this.postService.addPostToCollectionIfMissing(this.postsSharedCollection, postComment.post);
  }

  protected loadRelationshipsOptions(): void {
    this.postService
      .query()
      .pipe(map((res: HttpResponse<IPost[]>) => res.body ?? []))
      .pipe(map((posts: IPost[]) => this.postService.addPostToCollectionIfMissing(posts, this.editForm.get('post')!.value)))
      .subscribe((posts: IPost[]) => (this.postsSharedCollection = posts));
  }

  protected createFromForm(): IPostComment {
    return {
      ...new PostComment(),
      id: this.editForm.get(['id'])!.value,
      commentText: this.editForm.get(['commentText'])!.value,
      post: this.editForm.get(['post'])!.value,
    };
  }
}
