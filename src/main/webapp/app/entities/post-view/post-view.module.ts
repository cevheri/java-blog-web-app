import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PostViewComponent } from './list/post-view.component';
import { PostViewDetailComponent } from './detail/post-view-detail.component';
import { PostViewUpdateComponent } from './update/post-view-update.component';
import { PostViewDeleteDialogComponent } from './delete/post-view-delete-dialog.component';
import { PostViewRoutingModule } from './route/post-view-routing.module';

@NgModule({
  imports: [SharedModule, PostViewRoutingModule],
  declarations: [PostViewComponent, PostViewDetailComponent, PostViewUpdateComponent, PostViewDeleteDialogComponent],
  entryComponents: [PostViewDeleteDialogComponent],
})
export class PostViewModule {}
