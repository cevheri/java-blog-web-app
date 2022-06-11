import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IntegrationLogComponent } from './list/integration-log.component';
import { IntegrationLogDetailComponent } from './detail/integration-log-detail.component';
import { IntegrationLogUpdateComponent } from './update/integration-log-update.component';
import { IntegrationLogDeleteDialogComponent } from './delete/integration-log-delete-dialog.component';
import { IntegrationLogRoutingModule } from './route/integration-log-routing.module';

@NgModule({
  imports: [SharedModule, IntegrationLogRoutingModule],
  declarations: [
    IntegrationLogComponent,
    IntegrationLogDetailComponent,
    IntegrationLogUpdateComponent,
    IntegrationLogDeleteDialogComponent,
  ],
  entryComponents: [IntegrationLogDeleteDialogComponent],
})
export class IntegrationLogModule {}
