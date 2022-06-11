import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'blog',
        data: { pageTitle: 'blogApp.blog.home.title' },
        loadChildren: () => import('./blog/blog.module').then(m => m.BlogModule),
      },
      {
        path: 'post',
        data: { pageTitle: 'blogApp.post.home.title' },
        loadChildren: () => import('./post/post.module').then(m => m.PostModule),
      },
      {
        path: 'tag',
        data: { pageTitle: 'blogApp.tag.home.title' },
        loadChildren: () => import('./tag/tag.module').then(m => m.TagModule),
      },
      {
        path: 'post-comment',
        data: { pageTitle: 'blogApp.postComment.home.title' },
        loadChildren: () => import('./post-comment/post-comment.module').then(m => m.PostCommentModule),
      },
      {
        path: 'post-view',
        data: { pageTitle: 'blogApp.postView.home.title' },
        loadChildren: () => import('./post-view/post-view.module').then(m => m.PostViewModule),
      },
      {
        path: 'post-like',
        data: { pageTitle: 'blogApp.postLike.home.title' },
        loadChildren: () => import('./post-like/post-like.module').then(m => m.PostLikeModule),
      },
      {
        path: 'third-party-app',
        data: { pageTitle: 'blogApp.thirdPartyApp.home.title' },
        loadChildren: () => import('./third-party-app/third-party-app.module').then(m => m.ThirdPartyAppModule),
      },
      {
        path: 'integration-log',
        data: { pageTitle: 'blogApp.integrationLog.home.title' },
        loadChildren: () => import('./integration-log/integration-log.module').then(m => m.IntegrationLogModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
