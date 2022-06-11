import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostComment } from '../post-comment.model';

@Component({
  selector: 'jhi-post-comment-detail',
  templateUrl: './post-comment-detail.component.html',
})
export class PostCommentDetailComponent implements OnInit {
  postComment: IPostComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postComment }) => {
      this.postComment = postComment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
