import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntegrationLog } from '../integration-log.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-integration-log-detail',
  templateUrl: './integration-log-detail.component.html',
})
export class IntegrationLogDetailComponent implements OnInit {
  integrationLog: IIntegrationLog | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ integrationLog }) => {
      this.integrationLog = integrationLog;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
