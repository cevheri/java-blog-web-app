import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IntegrationLogService } from '../service/integration-log.service';
import { IIntegrationLog, IntegrationLog } from '../integration-log.model';

import { IntegrationLogUpdateComponent } from './integration-log-update.component';

describe('IntegrationLog Management Update Component', () => {
  let comp: IntegrationLogUpdateComponent;
  let fixture: ComponentFixture<IntegrationLogUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let integrationLogService: IntegrationLogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IntegrationLogUpdateComponent],
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
      .overrideTemplate(IntegrationLogUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IntegrationLogUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    integrationLogService = TestBed.inject(IntegrationLogService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const integrationLog: IIntegrationLog = { id: 456 };

      activatedRoute.data = of({ integrationLog });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(integrationLog));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IntegrationLog>>();
      const integrationLog = { id: 123 };
      jest.spyOn(integrationLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ integrationLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: integrationLog }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(integrationLogService.update).toHaveBeenCalledWith(integrationLog);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IntegrationLog>>();
      const integrationLog = new IntegrationLog();
      jest.spyOn(integrationLogService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ integrationLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: integrationLog }));
      saveSubject.complete();

      // THEN
      expect(integrationLogService.create).toHaveBeenCalledWith(integrationLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IntegrationLog>>();
      const integrationLog = { id: 123 };
      jest.spyOn(integrationLogService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ integrationLog });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(integrationLogService.update).toHaveBeenCalledWith(integrationLog);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
