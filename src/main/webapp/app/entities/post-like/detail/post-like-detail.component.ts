import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostLike } from '../post-like.model';

@Component({
  selector: 'jhi-post-like-detail',
  templateUrl: './post-like-detail.component.html',
})
export class PostLikeDetailComponent implements OnInit {
  postLike: IPostLike | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postLike }) => {
      this.postLike = postLike;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
