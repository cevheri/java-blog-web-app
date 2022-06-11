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

describe('IntegrationLog e2e test', () => {
  const integrationLogPageUrl = '/integration-log';
  const integrationLogPageUrlPattern = new RegExp('/integration-log(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const integrationLogSample = {};

  let integrationLog: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/integration-logs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/integration-logs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/integration-logs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (integrationLog) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/integration-logs/${integrationLog.id}`,
      }).then(() => {
        integrationLog = undefined;
      });
    }
  });

  it('IntegrationLogs menu should load IntegrationLogs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('integration-log');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IntegrationLog').should('exist');
    cy.url().should('match', integrationLogPageUrlPattern);
  });

  describe('IntegrationLog page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(integrationLogPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IntegrationLog page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/integration-log/new$'));
        cy.getEntityCreateUpdateHeading('IntegrationLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', integrationLogPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/integration-logs',
          body: integrationLogSample,
        }).then(({ body }) => {
          integrationLog = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/integration-logs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/integration-logs?page=0&size=20>; rel="last",<http://localhost/api/integration-logs?page=0&size=20>; rel="first"',
              },
              body: [integrationLog],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(integrationLogPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IntegrationLog page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('integrationLog');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', integrationLogPageUrlPattern);
      });

      it('edit button click should load edit IntegrationLog page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IntegrationLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', integrationLogPageUrlPattern);
      });

      it('last delete button click should delete instance of IntegrationLog', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('integrationLog').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', integrationLogPageUrlPattern);

        integrationLog = undefined;
      });
    });
  });

  describe('new IntegrationLog page', () => {
    beforeEach(() => {
      cy.visit(`${integrationLogPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IntegrationLog');
    });

    it('should create an instance of IntegrationLog', () => {
      cy.get(`[data-cy="createdBy"]`).type('Steel Plastic utilisation').should('have.value', 'Steel Plastic utilisation');

      cy.get(`[data-cy="createdDate"]`).type('2022-06-11T06:09').blur().should('have.value', '2022-06-11T06:09');

      cy.get(`[data-cy="integrationName"]`).select('MEDIUM');

      cy.get(`[data-cy="apiUrl"]`).type('testing Rhode synthesize').should('have.value', 'testing Rhode synthesize');

      cy.get(`[data-cy="exitCode"]`).select('SUCCESS');

      cy.get(`[data-cy="requestData"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="responseData"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="errorCode"]`).type('Cambridgeshire Intelligent').should('have.value', 'Cambridgeshire Intelligent');

      cy.get(`[data-cy="errorMessage"]`)
        .type('Integration contextually-based Bedfordshire')
        .should('have.value', 'Integration contextually-based Bedfordshire');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        integrationLog = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', integrationLogPageUrlPattern);
    });
  });
});
