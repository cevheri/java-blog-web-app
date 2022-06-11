import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PostViewComponent } from '../list/post-view.component';
import { PostViewDetailComponent } from '../detail/post-view-detail.component';
import { PostViewUpdateComponent } from '../update/post-view-update.component';
import { PostViewRoutingResolveService } from './post-view-routing-resolve.service';

const postViewRoute: Routes = [
  {
    path: '',
    component: PostViewComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PostViewDetailComponent,
    resolve: {
      postView: PostViewRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PostViewUpdateComponent,
    resolve: {
      postView: PostViewRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PostViewUpdateComponent,
    resolve: {
      postView: PostViewRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(postViewRoute)],
  exports: [RouterModule],
})
export class PostViewRoutingModule {}
