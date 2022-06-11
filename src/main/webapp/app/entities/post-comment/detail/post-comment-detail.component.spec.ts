import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PostCommentDetailComponent } from './post-comment-detail.component';

describe('PostComment Management Detail Component', () => {
  let comp: PostCommentDetailComponent;
  let fixture: ComponentFixture<PostCommentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostCommentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ postComment: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PostCommentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PostCommentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load postComment on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.postComment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
