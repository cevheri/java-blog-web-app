import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PostLikeComponent } from './list/post-like.component';
import { PostLikeDetailComponent } from './detail/post-like-detail.component';
import { PostLikeUpdateComponent } from './update/post-like-update.component';
import { PostLikeDeleteDialogComponent } from './delete/post-like-delete-dialog.component';
import { PostLikeRoutingModule } from './route/post-like-routing.module';

@NgModule({
  imports: [SharedModule, PostLikeRoutingModule],
  declarations: [PostLikeComponent, PostLikeDetailComponent, PostLikeUpdateComponent, PostLikeDeleteDialogComponent],
  entryComponents: [PostLikeDeleteDialogComponent],
})
export class PostLikeModule {}
