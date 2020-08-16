import { Component, OnInit } from '@angular/core';
import { ServiceHandlerService } from '../service-handler.service';

@Component({
  selector: 'app-get-delete',
  templateUrl: './get-delete.component.html',
  styleUrls: ['./get-delete.component.css']
})
export class GetDeleteComponent implements OnInit {

  services: any;
  id: number;

  constructor(private handler: ServiceHandlerService) { }

  public deleteService(id: number) {
    let resp = this.handler.deleteService(id);
    resp.subscribe((data) => this.services = data); 
  }


  ngOnInit() {
    console.log('Get All Service.')
    let resp = this.handler.getServices();
    resp.subscribe((data) => this.services = data);
  }
}
