import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PostCommentComponent } from '../list/post-comment.component';
import { PostCommentDetailComponent } from '../detail/post-comment-detail.component';
import { PostCommentUpdateComponent } from '../update/post-comment-update.component';
import { PostCommentRoutingResolveService } from './post-comment-routing-resolve.service';

const postCommentRoute: Routes = [
  {
    path: '',
    component: PostCommentComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PostCommentDetailComponent,
    resolve: {
      postComment: PostCommentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PostCommentUpdateComponent,
    resolve: {
      postComment: PostCommentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PostCommentUpdateComponent,
    resolve: {
      postComment: PostCommentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(postCommentRoute)],
  exports: [RouterModule],
})
export class PostCommentRoutingModule {}
