import { TestBed } from '@angular/core/testing';

import { ServiceHandlerService } from './service-handler.service';

describe('ServiceHandlerService', () => {
  let service: ServiceHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
