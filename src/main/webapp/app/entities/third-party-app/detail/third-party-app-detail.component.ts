import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThirdPartyApp } from '../third-party-app.model';

@Component({
  selector: 'jhi-third-party-app-detail',
  templateUrl: './third-party-app-detail.component.html',
})
export class ThirdPartyAppDetailComponent implements OnInit {
  thirdPartyApp: IThirdPartyApp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thirdPartyApp }) => {
      this.thirdPartyApp = thirdPartyApp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
