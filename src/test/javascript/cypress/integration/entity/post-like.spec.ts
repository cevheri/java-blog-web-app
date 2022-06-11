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

describe('PostLike e2e test', () => {
  const postLikePageUrl = '/post-like';
  const postLikePageUrlPattern = new RegExp('/post-like(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const postLikeSample = {};

  let postLike: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/post-likes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/post-likes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/post-likes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (postLike) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/post-likes/${postLike.id}`,
      }).then(() => {
        postLike = undefined;
      });
    }
  });

  it('PostLikes menu should load PostLikes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('post-like');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PostLike').should('exist');
    cy.url().should('match', postLikePageUrlPattern);
  });

  describe('PostLike page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(postLikePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PostLike page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/post-like/new$'));
        cy.getEntityCreateUpdateHeading('PostLike');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postLikePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/post-likes',
          body: postLikeSample,
        }).then(({ body }) => {
          postLike = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/post-likes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/post-likes?page=0&size=20>; rel="last",<http://localhost/api/post-likes?page=0&size=20>; rel="first"',
              },
              body: [postLike],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(postLikePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PostLike page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('postLike');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postLikePageUrlPattern);
      });

      it('edit button click should load edit PostLike page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PostLike');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postLikePageUrlPattern);
      });

      it('last delete button click should delete instance of PostLike', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('postLike').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', postLikePageUrlPattern);

        postLike = undefined;
      });
    });
  });

  describe('new PostLike page', () => {
    beforeEach(() => {
      cy.visit(`${postLikePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PostLike');
    });

    it('should create an instance of PostLike', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        postLike = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', postLikePageUrlPattern);
    });
  });
});
