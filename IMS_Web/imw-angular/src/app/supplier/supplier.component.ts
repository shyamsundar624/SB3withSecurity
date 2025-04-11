import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-supplier',
  imports: [CommonModule],
  templateUrl: './supplier.component.html',
  styleUrl: './supplier.component.css'
})
export class SupplierComponent implements OnInit{
  suppliers:any[]=[];
  message:string='';

  constructor(private apiService:ApiService,
    private router:Router 
  ){}

  ngOnInit(): void {
    this.getSuppliers();
  }

  getSuppliers():void{
    this.apiService.getAllSupplier().subscribe({
      next:(res:any)=>{
        if(res.status===200){
        this.suppliers=res.suppliers;
        }else{
          this.showMessage(res.message);
        }
      },
      error:(error)=>{
        this.showMessage(error?.error?.message || error?.message || "Unable To Fetch Supplier "+error);
      } 
    })
  }

//Navigate to add supplier Page
navigateToAddSupplier():void{
  this.router.navigate([`/add-supplier`]);
}

//Navigate to edit supplier Page
navigateToEditSupplier(supplierId:string):void{
  this.router.navigate([`/edit-supplier/${supplierId}`]);
}

handleDeleteSupplier(supplierId:string):void{
  if(window.confirm("Are you sure you want to delete this Supplier")){
    this.apiService.deleteSupplier(supplierId).subscribe({
      next:(res:any)=>{
        if(res.status===200){
          this.showMessage("Supplier Deleted Successfully");
          this.getSuppliers();
        }
      },
      error:(error)=>{
        this.showMessage(error?.error?.message || error?.message ||"Unable To Delete Supplier"+error )
      }
    })
  }
}

  showMessage(message:string){
    this.message=message;
    setTimeout(()=>{
      this.message=''
    },4000)
  }
}
