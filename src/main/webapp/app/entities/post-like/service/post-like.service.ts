import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPostLike, getPostLikeIdentifier } from '../post-like.model';

export type EntityResponseType = HttpResponse<IPostLike>;
export type EntityArrayResponseType = HttpResponse<IPostLike[]>;

@Injectable({ providedIn: 'root' })
export class PostLikeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/post-likes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(postLike: IPostLike): Observable<EntityResponseType> {
    return this.http.post<IPostLike>(this.resourceUrl, postLike, { observe: 'response' });
  }

  update(postLike: IPostLike): Observable<EntityResponseType> {
    return this.http.put<IPostLike>(`${this.resourceUrl}/${getPostLikeIdentifier(postLike) as number}`, postLike, { observe: 'response' });
  }

  partialUpdate(postLike: IPostLike): Observable<EntityResponseType> {
    return this.http.patch<IPostLike>(`${this.resourceUrl}/${getPostLikeIdentifier(postLike) as number}`, postLike, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPostLike>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPostLike[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPostLikeToCollectionIfMissing(postLikeCollection: IPostLike[], ...postLikesToCheck: (IPostLike | null | undefined)[]): IPostLike[] {
    const postLikes: IPostLike[] = postLikesToCheck.filter(isPresent);
    if (postLikes.length > 0) {
      const postLikeCollectionIdentifiers = postLikeCollection.map(postLikeItem => getPostLikeIdentifier(postLikeItem)!);
      const postLikesToAdd = postLikes.filter(postLikeItem => {
        const postLikeIdentifier = getPostLikeIdentifier(postLikeItem);
        if (postLikeIdentifier == null || postLikeCollectionIdentifiers.includes(postLikeIdentifier)) {
          return false;
        }
        postLikeCollectionIdentifiers.push(postLikeIdentifier);
        return true;
      });
      return [...postLikesToAdd, ...postLikeCollection];
    }
    return postLikeCollection;
  }
}
