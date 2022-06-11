import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThirdPartyApp, ThirdPartyApp } from '../third-party-app.model';
import { ThirdPartyAppService } from '../service/third-party-app.service';

@Injectable({ providedIn: 'root' })
export class ThirdPartyAppRoutingResolveService implements Resolve<IThirdPartyApp> {
  constructor(protected service: ThirdPartyAppService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThirdPartyApp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((thirdPartyApp: HttpResponse<ThirdPartyApp>) => {
          if (thirdPartyApp.body) {
            return of(thirdPartyApp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ThirdPartyApp());
  }
}
