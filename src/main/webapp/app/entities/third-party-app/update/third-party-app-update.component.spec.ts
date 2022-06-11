import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ThirdPartyAppService } from '../service/third-party-app.service';
import { IThirdPartyApp, ThirdPartyApp } from '../third-party-app.model';

import { ThirdPartyAppUpdateComponent } from './third-party-app-update.component';

describe('ThirdPartyApp Management Update Component', () => {
  let comp: ThirdPartyAppUpdateComponent;
  let fixture: ComponentFixture<ThirdPartyAppUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let thirdPartyAppService: ThirdPartyAppService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ThirdPartyAppUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ThirdPartyAppUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ThirdPartyAppUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    thirdPartyAppService = TestBed.inject(ThirdPartyAppService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const thirdPartyApp: IThirdPartyApp = { id: 456 };

      activatedRoute.data = of({ thirdPartyApp });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(thirdPartyApp));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThirdPartyApp>>();
      const thirdPartyApp = { id: 123 };
      jest.spyOn(thirdPartyAppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thirdPartyApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thirdPartyApp }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(thirdPartyAppService.update).toHaveBeenCalledWith(thirdPartyApp);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThirdPartyApp>>();
      const thirdPartyApp = new ThirdPartyApp();
      jest.spyOn(thirdPartyAppService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thirdPartyApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: thirdPartyApp }));
      saveSubject.complete();

      // THEN
      expect(thirdPartyAppService.create).toHaveBeenCalledWith(thirdPartyApp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ThirdPartyApp>>();
      const thirdPartyApp = { id: 123 };
      jest.spyOn(thirdPartyAppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ thirdPartyApp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(thirdPartyAppService.update).toHaveBeenCalledWith(thirdPartyApp);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
