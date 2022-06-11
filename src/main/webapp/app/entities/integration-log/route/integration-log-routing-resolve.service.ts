import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIntegrationLog, IntegrationLog } from '../integration-log.model';
import { IntegrationLogService } from '../service/integration-log.service';

@Injectable({ providedIn: 'root' })
export class IntegrationLogRoutingResolveService implements Resolve<IIntegrationLog> {
  constructor(protected service: IntegrationLogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIntegrationLog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((integrationLog: HttpResponse<IntegrationLog>) => {
          if (integrationLog.body) {
            return of(integrationLog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IntegrationLog());
  }
}
