import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PostLikeComponent } from '../list/post-like.component';
import { PostLikeDetailComponent } from '../detail/post-like-detail.component';
import { PostLikeUpdateComponent } from '../update/post-like-update.component';
import { PostLikeRoutingResolveService } from './post-like-routing-resolve.service';

const postLikeRoute: Routes = [
  {
    path: '',
    component: PostLikeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PostLikeDetailComponent,
    resolve: {
      postLike: PostLikeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PostLikeUpdateComponent,
    resolve: {
      postLike: PostLikeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PostLikeUpdateComponent,
    resolve: {
      postLike: PostLikeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(postLikeRoute)],
  exports: [RouterModule],
})
export class PostLikeRoutingModule {}
