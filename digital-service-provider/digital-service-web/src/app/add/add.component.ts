import { Component, OnInit } from '@angular/core';
import { Service } from '../service'
import { ServiceHandlerService } from '../service-handler.service'

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  service: Service = new Service("", "", "", "",null,null);
  message: any;

  constructor(private handler: ServiceHandlerService) { }

  ngOnInit() {
  }

  public registerNow() {
    let resp = this.handler.addService(this.service);
    resp.subscribe((data) => this.message = data);
  }

}
