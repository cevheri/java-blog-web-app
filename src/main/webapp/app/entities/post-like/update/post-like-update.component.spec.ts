import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PostLikeService } from '../service/post-like.service';
import { IPostLike, PostLike } from '../post-like.model';
import { IPost } from 'app/entities/post/post.model';
import { PostService } from 'app/entities/post/service/post.service';

import { PostLikeUpdateComponent } from './post-like-update.component';

describe('PostLike Management Update Component', () => {
  let comp: PostLikeUpdateComponent;
  let fixture: ComponentFixture<PostLikeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let postLikeService: PostLikeService;
  let postService: PostService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PostLikeUpdateComponent],
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
      .overrideTemplate(PostLikeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PostLikeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    postLikeService = TestBed.inject(PostLikeService);
    postService = TestBed.inject(PostService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Post query and add missing value', () => {
      const postLike: IPostLike = { id: 456 };
      const post: IPost = { id: 65710 };
      postLike.post = post;

      const postCollection: IPost[] = [{ id: 97686 }];
      jest.spyOn(postService, 'query').mockReturnValue(of(new HttpResponse({ body: postCollection })));
      const additionalPosts = [post];
      const expectedCollection: IPost[] = [...additionalPosts, ...postCollection];
      jest.spyOn(postService, 'addPostToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ postLike });
      comp.ngOnInit();

      expect(postService.query).toHaveBeenCalled();
      expect(postService.addPostToCollectionIfMissing).toHaveBeenCalledWith(postCollection, ...additionalPosts);
      expect(comp.postsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const postLike: IPostLike = { id: 456 };
      const post: IPost = { id: 32467 };
      postLike.post = post;

      activatedRoute.data = of({ postLike });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(postLike));
      expect(comp.postsSharedCollection).toContain(post);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PostLike>>();
      const postLike = { id: 123 };
      jest.spyOn(postLikeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postLike });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: postLike }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(postLikeService.update).toHaveBeenCalledWith(postLike);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PostLike>>();
      const postLike = new PostLike();
      jest.spyOn(postLikeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postLike });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: postLike }));
      saveSubject.complete();

      // THEN
      expect(postLikeService.create).toHaveBeenCalledWith(postLike);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PostLike>>();
      const postLike = { id: 123 };
      jest.spyOn(postLikeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postLike });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(postLikeService.update).toHaveBeenCalledWith(postLike);
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
