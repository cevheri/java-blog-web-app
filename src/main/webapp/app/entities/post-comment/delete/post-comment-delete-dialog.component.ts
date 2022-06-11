import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPostComment } from '../post-comment.model';
import { PostCommentService } from '../service/post-comment.service';

@Component({
  templateUrl: './post-comment-delete-dialog.component.html',
})
export class PostCommentDeleteDialogComponent {
  postComment?: IPostComment;

  constructor(protected postCommentService: PostCommentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.postCommentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
