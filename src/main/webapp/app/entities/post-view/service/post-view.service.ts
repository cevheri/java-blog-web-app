import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPostView, getPostViewIdentifier } from '../post-view.model';

export type EntityResponseType = HttpResponse<IPostView>;
export type EntityArrayResponseType = HttpResponse<IPostView[]>;

@Injectable({ providedIn: 'root' })
export class PostViewService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/post-views');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(postView: IPostView): Observable<EntityResponseType> {
    return this.http.post<IPostView>(this.resourceUrl, postView, { observe: 'response' });
  }

  update(postView: IPostView): Observable<EntityResponseType> {
    return this.http.put<IPostView>(`${this.resourceUrl}/${getPostViewIdentifier(postView) as number}`, postView, { observe: 'response' });
  }

  partialUpdate(postView: IPostView): Observable<EntityResponseType> {
    return this.http.patch<IPostView>(`${this.resourceUrl}/${getPostViewIdentifier(postView) as number}`, postView, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPostView>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPostView[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPostViewToCollectionIfMissing(postViewCollection: IPostView[], ...postViewsToCheck: (IPostView | null | undefined)[]): IPostView[] {
    const postViews: IPostView[] = postViewsToCheck.filter(isPresent);
    if (postViews.length > 0) {
      const postViewCollectionIdentifiers = postViewCollection.map(postViewItem => getPostViewIdentifier(postViewItem)!);
      const postViewsToAdd = postViews.filter(postViewItem => {
        const postViewIdentifier = getPostViewIdentifier(postViewItem);
        if (postViewIdentifier == null || postViewCollectionIdentifiers.includes(postViewIdentifier)) {
          return false;
        }
        postViewCollectionIdentifiers.push(postViewIdentifier);
        return true;
      });
      return [...postViewsToAdd, ...postViewCollection];
    }
    return postViewCollection;
  }
}
