import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPostComment, PostComment } from '../post-comment.model';
import { PostCommentService } from '../service/post-comment.service';

@Injectable({ providedIn: 'root' })
export class PostCommentRoutingResolveService implements Resolve<IPostComment> {
  constructor(protected service: PostCommentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPostComment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((postComment: HttpResponse<PostComment>) => {
          if (postComment.body) {
            return of(postComment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PostComment());
  }
}
