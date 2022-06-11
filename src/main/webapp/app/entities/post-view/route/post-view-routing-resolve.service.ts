import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPostView, PostView } from '../post-view.model';
import { PostViewService } from '../service/post-view.service';

@Injectable({ providedIn: 'root' })
export class PostViewRoutingResolveService implements Resolve<IPostView> {
  constructor(protected service: PostViewService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPostView> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((postView: HttpResponse<PostView>) => {
          if (postView.body) {
            return of(postView.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PostView());
  }
}
