import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ThirdPartyAppComponent } from './list/third-party-app.component';
import { ThirdPartyAppDetailComponent } from './detail/third-party-app-detail.component';
import { ThirdPartyAppUpdateComponent } from './update/third-party-app-update.component';
import { ThirdPartyAppDeleteDialogComponent } from './delete/third-party-app-delete-dialog.component';
import { ThirdPartyAppRoutingModule } from './route/third-party-app-routing.module';

@NgModule({
  imports: [SharedModule, ThirdPartyAppRoutingModule],
  declarations: [ThirdPartyAppComponent, ThirdPartyAppDetailComponent, ThirdPartyAppUpdateComponent, ThirdPartyAppDeleteDialogComponent],
  entryComponents: [ThirdPartyAppDeleteDialogComponent],
})
export class ThirdPartyAppModule {}
