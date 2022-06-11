import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IntegrationLogComponent } from '../list/integration-log.component';
import { IntegrationLogDetailComponent } from '../detail/integration-log-detail.component';
import { IntegrationLogUpdateComponent } from '../update/integration-log-update.component';
import { IntegrationLogRoutingResolveService } from './integration-log-routing-resolve.service';

const integrationLogRoute: Routes = [
  {
    path: '',
    component: IntegrationLogComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IntegrationLogDetailComponent,
    resolve: {
      integrationLog: IntegrationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IntegrationLogUpdateComponent,
    resolve: {
      integrationLog: IntegrationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IntegrationLogUpdateComponent,
    resolve: {
      integrationLog: IntegrationLogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(integrationLogRoute)],
  exports: [RouterModule],
})
export class IntegrationLogRoutingModule {}
