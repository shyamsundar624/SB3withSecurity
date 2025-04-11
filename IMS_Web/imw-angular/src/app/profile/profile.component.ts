import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';

@Component({
  selector: 'app-profile',
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{

  user:any=null;
  message:string='';


  constructor(
    private apiService:ApiService
  ){}

  ngOnInit(): void {
   this.fetchUserInfo();
  }

  fetchUserInfo() {
    this.apiService.getLoggedInUserInfo().subscribe({
      next:(res:any)=>{
        this.user=res;
      },
      error:(error)=>{
        this.showMessage(error?.error?.message
          || error?.message
          || "Unable to Fetch User Info"
        )
      }
    })
  }

  showMessage(message:string){
    this.message=message;

    setTimeout(() => {
      this.message='';
    },4000);
  }
}
