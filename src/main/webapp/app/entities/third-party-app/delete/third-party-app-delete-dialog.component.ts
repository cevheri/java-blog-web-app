import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IThirdPartyApp } from '../third-party-app.model';
import { ThirdPartyAppService } from '../service/third-party-app.service';

@Component({
  templateUrl: './third-party-app-delete-dialog.component.html',
})
export class ThirdPartyAppDeleteDialogComponent {
  thirdPartyApp?: IThirdPartyApp;

  constructor(protected thirdPartyAppService: ThirdPartyAppService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thirdPartyAppService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
