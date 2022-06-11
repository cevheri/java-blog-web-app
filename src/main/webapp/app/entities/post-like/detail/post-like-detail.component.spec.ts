import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PostLikeDetailComponent } from './post-like-detail.component';

describe('PostLike Management Detail Component', () => {
  let comp: PostLikeDetailComponent;
  let fixture: ComponentFixture<PostLikeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostLikeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ postLike: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PostLikeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PostLikeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load postLike on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.postLike).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
