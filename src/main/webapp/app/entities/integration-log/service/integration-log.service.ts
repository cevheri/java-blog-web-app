import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIntegrationLog, getIntegrationLogIdentifier } from '../integration-log.model';

export type EntityResponseType = HttpResponse<IIntegrationLog>;
export type EntityArrayResponseType = HttpResponse<IIntegrationLog[]>;

@Injectable({ providedIn: 'root' })
export class IntegrationLogService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/integration-logs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(integrationLog: IIntegrationLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(integrationLog);
    return this.http
      .post<IIntegrationLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(integrationLog: IIntegrationLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(integrationLog);
    return this.http
      .put<IIntegrationLog>(`${this.resourceUrl}/${getIntegrationLogIdentifier(integrationLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(integrationLog: IIntegrationLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(integrationLog);
    return this.http
      .patch<IIntegrationLog>(`${this.resourceUrl}/${getIntegrationLogIdentifier(integrationLog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIntegrationLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIntegrationLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIntegrationLogToCollectionIfMissing(
    integrationLogCollection: IIntegrationLog[],
    ...integrationLogsToCheck: (IIntegrationLog | null | undefined)[]
  ): IIntegrationLog[] {
    const integrationLogs: IIntegrationLog[] = integrationLogsToCheck.filter(isPresent);
    if (integrationLogs.length > 0) {
      const integrationLogCollectionIdentifiers = integrationLogCollection.map(
        integrationLogItem => getIntegrationLogIdentifier(integrationLogItem)!
      );
      const integrationLogsToAdd = integrationLogs.filter(integrationLogItem => {
        const integrationLogIdentifier = getIntegrationLogIdentifier(integrationLogItem);
        if (integrationLogIdentifier == null || integrationLogCollectionIdentifiers.includes(integrationLogIdentifier)) {
          return false;
        }
        integrationLogCollectionIdentifiers.push(integrationLogIdentifier);
        return true;
      });
      return [...integrationLogsToAdd, ...integrationLogCollection];
    }
    return integrationLogCollection;
  }

  protected convertDateFromClient(integrationLog: IIntegrationLog): IIntegrationLog {
    return Object.assign({}, integrationLog, {
      createdDate: integrationLog.createdDate?.isValid() ? integrationLog.createdDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((integrationLog: IIntegrationLog) => {
        integrationLog.createdDate = integrationLog.createdDate ? dayjs(integrationLog.createdDate) : undefined;
      });
    }
    return res;
  }
}
