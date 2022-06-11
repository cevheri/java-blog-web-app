import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPostView } from '../post-view.model';
import { PostViewService } from '../service/post-view.service';

@Component({
  templateUrl: './post-view-delete-dialog.component.html',
})
export class PostViewDeleteDialogComponent {
  postView?: IPostView;

  constructor(protected postViewService: PostViewService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.postViewService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
