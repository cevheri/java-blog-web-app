import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PostViewService } from '../service/post-view.service';
import { IPostView, PostView } from '../post-view.model';
import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';

import { PostViewUpdateComponent } from './post-view-update.component';

describe('PostView Management Update Component', () => {
  let comp: PostViewUpdateComponent;
  let fixture: ComponentFixture<PostViewUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let postViewService: PostViewService;
  let postService: PostService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PostViewUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PostViewUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PostViewUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    postViewService = TestBed.inject(PostViewService);
    postService = TestBed.inject(PostService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Post query and add missing value', () => {
      const postView: IPostView = { id: 456 };
      const post: IPost = { id: 71613 };
      postView.post = post;

      const postCollection: IPost[] = [{ id: 46540 }];
      jest.spyOn(postService, 'query').mockReturnValue(of(new HttpResponse({ body: postCollection })));
      const additionalPosts = [post];
      const expectedCollection: IPost[] = [...additionalPosts, ...postCollection];
      jest.spyOn(postService, 'addPostToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ postView });
      comp.ngOnInit();

      expect(postService.query).toHaveBeenCalled();
      expect(postService.addPostToCollectionIfMissing).toHaveBeenCalledWith(postCollection, ...additionalPosts);
      expect(comp.postsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const postView: IPostView = { id: 456 };
      const post: IPost = { id: 26655 };
      postView.post = post;

      activatedRoute.data = of({ postView });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(postView));
      expect(comp.postsSharedCollection).toContain(post);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PostView>>();
      const postView = { id: 123 };
      jest.spyOn(postViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: postView }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(postViewService.update).toHaveBeenCalledWith(postView);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PostView>>();
      const postView = new PostView();
      jest.spyOn(postViewService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: postView }));
      saveSubject.complete();

      // THEN
      expect(postViewService.create).toHaveBeenCalledWith(postView);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PostView>>();
      const postView = { id: 123 };
      jest.spyOn(postViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(postViewService.update).toHaveBeenCalledWith(postView);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPostById', () => {
      it('Should return tracked Post primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPostById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
