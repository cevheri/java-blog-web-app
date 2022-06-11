import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('PostComment e2e test', () => {
  const postCommentPageUrl = '/post-comment';
  const postCommentPageUrlPattern = new RegExp('/post-comment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const postCommentSample = { commentText: 'magenta Hat Functionality' };

  let postComment: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/post-comments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/post-comments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/post-comments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (postComment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/post-comments/${postComment.id}`,
      }).then(() => {
        postComment = undefined;
      });
    }
  });

  it('PostComments menu should load PostComments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('post-comment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PostComment').should('exist');
    cy.url().should('match', postCommentPageUrlPattern);
  });

  describe('PostComment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(postCommentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PostComment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/post-comment/new$'));
        cy.getEntityCreateUpdateHeading('PostComment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postCommentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/post-comments',
          body: postCommentSample,
        }).then(({ body }) => {
          postComment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/post-comments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/post-comments?page=0&size=20>; rel="last",<http://localhost/api/post-comments?page=0&size=20>; rel="first"',
              },
              body: [postComment],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(postCommentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PostComment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('postComment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postCommentPageUrlPattern);
      });

      it('edit button click should load edit PostComment page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PostComment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postCommentPageUrlPattern);
      });

      it('last delete button click should delete instance of PostComment', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('postComment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postCommentPageUrlPattern);

        postComment = undefined;
      });
    });
  });

  describe('new PostComment page', () => {
    beforeEach(() => {
      cy.visit(`${postCommentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PostComment');
    });

    it('should create an instance of PostComment', () => {
      cy.get(`[data-cy="commentText"]`).type('Account mobile').should('have.value', 'Account mobile');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        postComment = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', postCommentPageUrlPattern);
    });
  });
});
