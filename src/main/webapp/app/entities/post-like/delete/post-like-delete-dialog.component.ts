import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPostLike } from '../post-like.model';
import { PostLikeService } from '../service/post-like.service';

@Component({
  templateUrl: './post-like-delete-dialog.component.html',
})
export class PostLikeDeleteDialogComponent {
  postLike?: IPostLike;

  constructor(protected postLikeService: PostLikeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.postLikeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
