import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostView } from '../post-view.model';

@Component({
  selector: 'jhi-post-view-detail',
  templateUrl: './post-view-detail.component.html',
})
export class PostViewDetailComponent implements OnInit {
  postView: IPostView | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postView }) => {
      this.postView = postView;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
