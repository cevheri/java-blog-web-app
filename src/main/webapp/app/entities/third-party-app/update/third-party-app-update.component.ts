import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IThirdPartyApp, ThirdPartyApp } from '../third-party-app.model';
import { ThirdPartyAppService } from '../service/third-party-app.service';
import { ThirdPartyAppName } from 'app/entities/enumerations/third-party-app-name.model';

@Component({
  selector: 'jhi-third-party-app-update',
  templateUrl: './third-party-app-update.component.html',
})
export class ThirdPartyAppUpdateComponent implements OnInit {
  isSaving = false;
  thirdPartyAppNameValues = Object.keys(ThirdPartyAppName);

  editForm = this.fb.group({
    id: [],
    name: [],
    baseUrl: [null, [Validators.maxLength(1000)]],
    accessKey: [null, [Validators.maxLength(1000)]],
    authorId: [null, [Validators.maxLength(1000)]],
    creatingPostApi: [null, [Validators.maxLength(1000)]],
    readPostApi: [null, [Validators.maxLength(1000)]],
    active: [],
  });

  constructor(protected thirdPartyAppService: ThirdPartyAppService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thirdPartyApp }) => {
      this.updateForm(thirdPartyApp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thirdPartyApp = this.createFromForm();
    if (thirdPartyApp.id !== undefined) {
      this.subscribeToSaveResponse(this.thirdPartyAppService.update(thirdPartyApp));
    } else {
      this.subscribeToSaveResponse(this.thirdPartyAppService.create(thirdPartyApp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThirdPartyApp>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(thirdPartyApp: IThirdPartyApp): void {
    this.editForm.patchValue({
      id: thirdPartyApp.id,
      name: thirdPartyApp.name,
      baseUrl: thirdPartyApp.baseUrl,
      accessKey: thirdPartyApp.accessKey,
      authorId: thirdPartyApp.authorId,
      creatingPostApi: thirdPartyApp.creatingPostApi,
      readPostApi: thirdPartyApp.readPostApi,
      active: thirdPartyApp.active,
    });
  }

  protected createFromForm(): IThirdPartyApp {
    return {
      ...new ThirdPartyApp(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      baseUrl: this.editForm.get(['baseUrl'])!.value,
      accessKey: this.editForm.get(['accessKey'])!.value,
      authorId: this.editForm.get(['authorId'])!.value,
      creatingPostApi: this.editForm.get(['creatingPostApi'])!.value,
      readPostApi: this.editForm.get(['readPostApi'])!.value,
      active: this.editForm.get(['active'])!.value,
    };
  }
}
