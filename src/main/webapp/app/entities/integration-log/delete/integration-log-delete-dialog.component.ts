import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIntegrationLog } from '../integration-log.model';
import { IntegrationLogService } from '../service/integration-log.service';

@Component({
  templateUrl: './integration-log-delete-dialog.component.html',
})
export class IntegrationLogDeleteDialogComponent {
  integrationLog?: IIntegrationLog;

  constructor(protected integrationLogService: IntegrationLogService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.integrationLogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
