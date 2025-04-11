import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../service/api.service';

@Component({
  selector: 'app-add-edit-supplier',
  imports: [FormsModule,CommonModule],
  templateUrl: './add-edit-supplier.component.html',
  styleUrl: './add-edit-supplier.component.css'
})
export class AddEditSupplierComponent implements OnInit{

  message:string='';
  isEditing:boolean=false;
  supplierId: string ='';

  formData:any={
    name:'',
    address:''
  }

  constructor(private apiService:ApiService,
    private router:Router
  ){}

  ngOnInit(): void {
    // Extracting supplier id from url
   this.supplierId=this.router.url.split('/')[2];
   if(this.supplierId){
    this.isEditing=true;
    this.fetchSupplier();
   }
  }

  fetchSupplier():void{
    this.apiService.getSupplierById(this.supplierId).subscribe({
      next:(res:any)=>{
        if(res.status===200){
        this.formData={
          name: res.supplier.name,
          address: res.supplier.address,
        };
      }
      },
      error:(error)=>{
        this.showMessage(error?.error?.message|| error?.message || "Enable to Fetch Supplier")
      }
    })
  }

  // Handle Form Submiision
handleSubmit(){
  if(!this.formData.name || !this.formData.address){
    this.showMessage("All Fields are Required");
    return;
  }
  //Prepare data for submission
  const supplierData={
    name:this.formData.name,
    address:this.formData.address
  };

  if(this.isEditing){
this.apiService.updateSupplier(this.supplierId,supplierData).subscribe({
  next:(res:any)=>{
    if(res.status===200){
this.showMessage("Supplier Updated Successfully");
this.router.navigate(['/supplier']);
    }
  },
  error:(error)=>{
    this.showMessage(error?.error?.message|| error?.message || "Unable to Edit Supplier "+error)
  }
  
})
  }else{
this.apiService.addSupplier(supplierData).subscribe({
  next:(res:any)=>{
    if(res.status===200){
      this.showMessage("Supplier Added Successfully");
      this.router.navigate(['/supplier']);
    }
  },
  error: (error)=>{
    this.showMessage(error?.error?.message || error?.message || "Unable to Add Supplier");
  }
})
  }
}

  showMessage(message:string){
    this.message=message;
    setTimeout(() => {
      this.message=''
    }, 4000);
  }
}
