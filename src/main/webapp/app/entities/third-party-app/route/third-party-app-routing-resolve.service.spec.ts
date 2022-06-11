import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IThirdPartyApp, ThirdPartyApp } from '../third-party-app.model';
import { ThirdPartyAppService } from '../service/third-party-app.service';

import { ThirdPartyAppRoutingResolveService } from './third-party-app-routing-resolve.service';

describe('ThirdPartyApp routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ThirdPartyAppRoutingResolveService;
  let service: ThirdPartyAppService;
  let resultThirdPartyApp: IThirdPartyApp | undefined;

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
    routingResolveService = TestBed.inject(ThirdPartyAppRoutingResolveService);
    service = TestBed.inject(ThirdPartyAppService);
    resultThirdPartyApp = undefined;
  });

  describe('resolve', () => {
    it('should return IThirdPartyApp returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultThirdPartyApp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultThirdPartyApp).toEqual({ id: 123 });
    });

    it('should return new IThirdPartyApp if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultThirdPartyApp = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultThirdPartyApp).toEqual(new ThirdPartyApp());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ThirdPartyApp })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultThirdPartyApp = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultThirdPartyApp).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
