import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPostLike, PostLike } from '../post-like.model';
import { PostLikeService } from '../service/post-like.service';

@Injectable({ providedIn: 'root' })
export class PostLikeRoutingResolveService implements Resolve<IPostLike> {
  constructor(protected service: PostLikeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPostLike> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((postLike: HttpResponse<PostLike>) => {
          if (postLike.body) {
            return of(postLike.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PostLike());
  }
}
