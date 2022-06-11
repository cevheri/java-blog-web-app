import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThirdPartyApp, getThirdPartyAppIdentifier } from '../third-party-app.model';

export type EntityResponseType = HttpResponse<IThirdPartyApp>;
export type EntityArrayResponseType = HttpResponse<IThirdPartyApp[]>;

@Injectable({ providedIn: 'root' })
export class ThirdPartyAppService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/third-party-apps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(thirdPartyApp: IThirdPartyApp): Observable<EntityResponseType> {
    return this.http.post<IThirdPartyApp>(this.resourceUrl, thirdPartyApp, { observe: 'response' });
  }

  update(thirdPartyApp: IThirdPartyApp): Observable<EntityResponseType> {
    return this.http.put<IThirdPartyApp>(`${this.resourceUrl}/${getThirdPartyAppIdentifier(thirdPartyApp) as number}`, thirdPartyApp, {
      observe: 'response',
    });
  }

  partialUpdate(thirdPartyApp: IThirdPartyApp): Observable<EntityResponseType> {
    return this.http.patch<IThirdPartyApp>(`${this.resourceUrl}/${getThirdPartyAppIdentifier(thirdPartyApp) as number}`, thirdPartyApp, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThirdPartyApp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThirdPartyApp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addThirdPartyAppToCollectionIfMissing(
    thirdPartyAppCollection: IThirdPartyApp[],
    ...thirdPartyAppsToCheck: (IThirdPartyApp | null | undefined)[]
  ): IThirdPartyApp[] {
    const thirdPartyApps: IThirdPartyApp[] = thirdPartyAppsToCheck.filter(isPresent);
    if (thirdPartyApps.length > 0) {
      const thirdPartyAppCollectionIdentifiers = thirdPartyAppCollection.map(
        thirdPartyAppItem => getThirdPartyAppIdentifier(thirdPartyAppItem)!
      );
      const thirdPartyAppsToAdd = thirdPartyApps.filter(thirdPartyAppItem => {
        const thirdPartyAppIdentifier = getThirdPartyAppIdentifier(thirdPartyAppItem);
        if (thirdPartyAppIdentifier == null || thirdPartyAppCollectionIdentifiers.includes(thirdPartyAppIdentifier)) {
          return false;
        }
        thirdPartyAppCollectionIdentifiers.push(thirdPartyAppIdentifier);
        return true;
      });
      return [...thirdPartyAppsToAdd, ...thirdPartyAppCollection];
    }
    return thirdPartyAppCollection;
  }
}
