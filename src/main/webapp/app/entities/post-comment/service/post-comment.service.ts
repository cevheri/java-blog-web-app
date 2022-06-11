import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPostComment, getPostCommentIdentifier } from '../post-comment.model';

export type EntityResponseType = HttpResponse<IPostComment>;
export type EntityArrayResponseType = HttpResponse<IPostComment[]>;

@Injectable({ providedIn: 'root' })
export class PostCommentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/post-comments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(postComment: IPostComment): Observable<EntityResponseType> {
    return this.http.post<IPostComment>(this.resourceUrl, postComment, { observe: 'response' });
  }

  update(postComment: IPostComment): Observable<EntityResponseType> {
    return this.http.put<IPostComment>(`${this.resourceUrl}/${getPostCommentIdentifier(postComment) as number}`, postComment, {
      observe: 'response',
    });
  }

  partialUpdate(postComment: IPostComment): Observable<EntityResponseType> {
    return this.http.patch<IPostComment>(`${this.resourceUrl}/${getPostCommentIdentifier(postComment) as number}`, postComment, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPostComment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPostComment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPostCommentToCollectionIfMissing(
    postCommentCollection: IPostComment[],
    ...postCommentsToCheck: (IPostComment | null | undefined)[]
  ): IPostComment[] {
    const postComments: IPostComment[] = postCommentsToCheck.filter(isPresent);
    if (postComments.length > 0) {
      const postCommentCollectionIdentifiers = postCommentCollection.map(postCommentItem => getPostCommentIdentifier(postCommentItem)!);
      const postCommentsToAdd = postComments.filter(postCommentItem => {
        const postCommentIdentifier = getPostCommentIdentifier(postCommentItem);
        if (postCommentIdentifier == null || postCommentCollectionIdentifiers.includes(postCommentIdentifier)) {
          return false;
        }
        postCommentCollectionIdentifiers.push(postCommentIdentifier);
        return true;
      });
      return [...postCommentsToAdd, ...postCommentCollection];
    }
    return postCommentCollection;
  }
}
