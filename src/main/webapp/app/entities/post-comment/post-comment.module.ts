import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PostCommentComponent } from './list/post-comment.component';
import { PostCommentDetailComponent } from './detail/post-comment-detail.component';
import { PostCommentUpdateComponent } from './update/post-comment-update.component';
import { PostCommentDeleteDialogComponent } from './delete/post-comment-delete-dialog.component';
import { PostCommentRoutingModule } from './route/post-comment-routing.module';

@NgModule({
  imports: [SharedModule, PostCommentRoutingModule],
  declarations: [PostCommentComponent, PostCommentDetailComponent, PostCommentUpdateComponent, PostCommentDeleteDialogComponent],
  entryComponents: [PostCommentDeleteDialogComponent],
})
export class PostCommentModule {}
