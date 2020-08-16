import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddComponent } from './add/add.component';
import { GetDeleteComponent } from './get-delete/get-delete.component';

const routes: Routes = [
  {path:"",redirectTo:"manage",pathMatch:"full"},
   {path:"add",component:AddComponent},
   {path:"manage",component:GetDeleteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
