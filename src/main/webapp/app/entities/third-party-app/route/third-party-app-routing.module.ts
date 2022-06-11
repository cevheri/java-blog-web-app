import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ThirdPartyAppComponent } from '../list/third-party-app.component';
import { ThirdPartyAppDetailComponent } from '../detail/third-party-app-detail.component';
import { ThirdPartyAppUpdateComponent } from '../update/third-party-app-update.component';
import { ThirdPartyAppRoutingResolveService } from './third-party-app-routing-resolve.service';

const thirdPartyAppRoute: Routes = [
  {
    path: '',
    component: ThirdPartyAppComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThirdPartyAppDetailComponent,
    resolve: {
      thirdPartyApp: ThirdPartyAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThirdPartyAppUpdateComponent,
    resolve: {
      thirdPartyApp: ThirdPartyAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThirdPartyAppUpdateComponent,
    resolve: {
      thirdPartyApp: ThirdPartyAppRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(thirdPartyAppRoute)],
  exports: [RouterModule],
})
export class ThirdPartyAppRoutingModule {}
