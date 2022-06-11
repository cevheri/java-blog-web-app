import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IIntegrationLog, IntegrationLog } from '../integration-log.model';
import { IntegrationLogService } from '../service/integration-log.service';

import { IntegrationLogRoutingResolveService } from './integration-log-routing-resolve.service';

describe('IntegrationLog routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: IntegrationLogRoutingResolveService;
  let service: IntegrationLogService;
  let resultIntegrationLog: IIntegrationLog | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(IntegrationLogRoutingResolveService);
    service = TestBed.inject(IntegrationLogService);
    resultIntegrationLog = undefined;
  });

  describe('resolve', () => {
    it('should return IIntegrationLog returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIntegrationLog = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIntegrationLog).toEqual({ id: 123 });
    });

    it('should return new IIntegrationLog if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIntegrationLog = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultIntegrationLog).toEqual(new IntegrationLog());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as IntegrationLog })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultIntegrationLog = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultIntegrationLog).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
