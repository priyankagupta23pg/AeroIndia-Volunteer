package com.aeroindia.view.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroindia.R;
import com.aeroindia.custom.utility.AppConstant;
import com.aeroindia.custom.utility.AppUtilityFunction;
import com.aeroindia.pojos.request.ContactEmailRequestModel;
import com.aeroindia.pojos.request.CreateProfileRequestModel;
import com.aeroindia.pojos.request.EmptyRequest;
import com.aeroindia.pojos.request.ExhibitorsItem;
import com.aeroindia.pojos.response.B2BListResponse;
import com.aeroindia.pojos.response.ContactEmailResponseModel;
import com.aeroindia.pojos.response.CreateProfileResponseModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.BaseAppCompatActivity;
import com.customComponent.utility.ProjectPrefrence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExhibitorListActivity extends BaseAppCompatActivity {
    private RelativeLayout backLayout;
    private TextView headerTV;
    private RecyclerView recyclerView;
private Context context;
    private B2BListResponse b2BListResponse;
    private ArrayList<ExhibitorsItem> exhibitorsItemList=new ArrayList<>();
    ExhibitorsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitor_list);
        context=this;
        getSupportActionBar().hide();
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        headerTV = (TextView) findViewById(R.id.headerTV);
        headerTV.setText("Exhibitor List");
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView= (RecyclerView)findViewById(R.id.recycleView);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getExhibitorData(new EmptyRequest());
    }
    private void getExhibitorData(EmptyRequest emptyRequest) {
       showHideProgressDialog(true);
        VolleyTaskListener taskListener=new VolleyTaskListener() {
            @Override
            public void postExecute(String response) {
                b2BListResponse = B2BListResponse.create(response);
                if (b2BListResponse != null) {
                    if (context!= null) {

                        if (b2BListResponse.isStatus()) {
                            if (b2BListResponse.companyHallList != null) {
                                exhibitorsItemList.clear();
                                // nocake.setText("There are "+getTrendingListResponse.result.size()+" cakes under this category");
                                for (int i = 0; i < b2BListResponse.companyHallList.size(); i++) {
                                    ExhibitorsItem exhibitorsItem;
                                    if(b2BListResponse.companyHallList.get(i).hall!=null) {
                                         exhibitorsItem = new ExhibitorsItem(b2BListResponse.companyHallList.get(i).hall.getName(), b2BListResponse.companyHallList.get(i).hall.getStall(), b2BListResponse.companyHallList.get(i).company.getCountry(), b2BListResponse.companyHallList.get(i).company.getName());
                                    }
                                    else {
                                         exhibitorsItem = new ExhibitorsItem("N.A.","N.A.", b2BListResponse.companyHallList.get(i).company.getCountry(), b2BListResponse.companyHallList.get(i).company.getName());

                                    }
                                        exhibitorsItem.setUniqueId(b2BListResponse.companyHallList.get(i).company.getUniqueId());
                                        exhibitorsItem.setCompanyId(b2BListResponse.companyHallList.get(i).company.getCompanyId());
                                        exhibitorsItem.setEmail(b2BListResponse.companyHallList.get(i).company.getContactEmailId());
                                        exhibitorsItem.setPhone(b2BListResponse.companyHallList.get(i).company.getMobileNumber());
                                        exhibitorsItemList.add(exhibitorsItem);

//                                    ExhibitorsItem existExhibitorItem= DatabaseOpration.getExhibitor(b2BListResponse.companyList.get(i).getUniqueId(),dbHelper);
//                                    if(existExhibitorItem==null) {
//                                        DatabaseOpration.saveExhibitor(exhibitorsItem, dbHelper);
//                                    }
//                                    else
//                                    {
//                                        DatabaseOpration.updateExhibitor(exhibitorsItem,existExhibitorItem.getUniqueId(), dbHelper);
//
//                                    }
                                    }



                                refreshExhibitorList(exhibitorsItemList);
                            } else {
                                CustomAlert.alertWithOk(context, "There is no exhibitor data");

                            }
                        }
                        showHideProgressDialog(false);
                    }
                }
            }


            @Override
            public void onError(VolleyError error) {
                if (context != null) {

               showHideProgressDialog(false);
                    if (error.getMessage() != null) {
                        if (error.getMessage().contains("java.net.UnknownHostException")) {
                            CustomAlert.alertWithOk(context, getResources().getString(R.string.internet_connection_message));

                        } else {
                            CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                        }
                    } else {
                        CustomAlert.alertWithOk(context, getResources().getString(R.string.server_issue));

                    }
                }


            }
        };
        CustomVolley volley=new CustomVolley(taskListener, AppConstant.EXHIBITOR_API,emptyRequest.serialize(),null,null,context);
        volley.execute();
    }
    private void  refreshExhibitorList(ArrayList<ExhibitorsItem> exhibitorsItemList){

        Collections.sort(exhibitorsItemList, new Comparator<ExhibitorsItem>() {
            @Override
            public int compare(ExhibitorsItem lhs, ExhibitorsItem rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
        adapter = new ExhibitorsListAdapter(exhibitorsItemList,context);
        recyclerView.setAdapter(adapter);
    }
    public class ExhibitorsListAdapter extends RecyclerView.Adapter<ExhibitorsListAdapter.MyViewHolder> {

        private List<ExhibitorsItem> list;
        private Context context;
        private LayoutInflater layoutInflater;
        private String screenName;
        private double latt, longg;
        private Dialog dialog2,viewProfileDialog;
        private CustomVolley volley;
        private ContactEmailResponseModel contactEmailResponseModel;
        private EditText aboutMeET;
        private CreateProfileResponseModel getProfileResponseModel;





        public class MyViewHolder extends RecyclerView.ViewHolder  {
            public TextView company_name_TV, address_name_TV;
            private LinearLayout button_layout, main_layout;
            private RelativeLayout contactLayout,viewProfLayout;
            LinearLayout layy;

            public MyViewHolder(View view) {
                super(view);
                company_name_TV = (TextView) view.findViewById(R.id.company_name_TV);
                address_name_TV = (TextView) view.findViewById(R.id.address_name_TV);
                button_layout = (LinearLayout) view.findViewById(R.id.button_layout);
                main_layout = (LinearLayout) view.findViewById(R.id.main_layout);
                contactLayout = (RelativeLayout) view.findViewById(R.id.contactLayout);
                viewProfLayout = (RelativeLayout) view.findViewById(R.id.viewProfLayout);
                layy = (LinearLayout) view.findViewById(R.id.layy);
                //  locLayout=(RelativeLayout)view.findViewById(R.id.locLayout);
            }



        }

        public ExhibitorsListAdapter(List<ExhibitorsItem> list, Context context, String screenName) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.list = list;
            this.screenName = screenName;
        }

        public ExhibitorsListAdapter(List<ExhibitorsItem> list, Context context, String screenName, double latt, double longg) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.list = list;
            this.screenName = screenName;
            this.latt = latt;
            this.longg = longg;
        }

        public ExhibitorsListAdapter(List<ExhibitorsItem> list, Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exhibitor_listt_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final ExhibitorsItem item = list.get(position);
            holder.company_name_TV.setText(item.getName() + " , " + item.getCountry());
            if (item.getHallNo() != null && item.getStalNo() != null) {
                holder.address_name_TV.setText("#Hall - " + item.getHallNo() + " , " + "#Stall - " + item.getStalNo());
            } else {
                holder.address_name_TV.setText("#Hall - " + "N.A." + " , " + "#Stall - " + "N.A.");

            }
            if (screenName != null && screenName.equalsIgnoreCase(AppConstant.B2BSCREEN)) {
                holder.button_layout.setVisibility(View.VISIBLE);
            }

            holder.viewProfLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CreateProfileRequestModel getProfileRequest=new CreateProfileRequestModel();
                    getProfileRequest.setExhibitorId(item.getCompanyId());
                    Log.d("Get Profile Request",getProfileRequest.serialize());
                    getProfile(getProfileRequest);
                    //   openViewProfileWindow(item);
                }
            });
            holder.layy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra("NAME",item.getName());
                    setResult(55,intent);
                    finish();//finishing activity
                }
            });


            holder.contactLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    initiateNewUSerPopup(item);

                  /*  if(item.getEmail()!=null && !item.getEmail().equalsIgnoreCase("")) {
                        sendMail("Test Email", "Hi this test email", item.getEmail());
                    }
                    else
                    {
                        Toast.makeText(context,"There is no contact email available for this company.",Toast.LENGTH_LONG).show();

                    }*/
                }
            });

//            holder.locLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                            Uri.parse("http://maps.google.com/maps?saddr="+latt+","+longg+"&daddr="+28.6126141+","+77.3633831));
//                    context.startActivity(intent);
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private void sendMail(String subject, String bodyText, String mailTo) {
            String mailto = "mailto:" + mailTo;
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(mailto));
            try {
                context.startActivity(emailIntent);
            } catch (ActivityNotFoundException e) {
                //TODO: Handle case where no email app is available
            }
        }

        private void initiateNewUSerPopup(final ExhibitorsItem item) {
            dialog2 = new Dialog(context);
            // dialog = new Dialog(this, R.style.NewDialog);
            dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            //  dialog.setCancelable(true);
            dialog2.setCanceledOnTouchOutside(true);
            //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
      /* WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
       lp.dimAmount = 0.6f;
       dialog2.getWindow().setAttributes(lp);*/
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialog2.getWindow().getAttributes().windowAnimations = R.style.animation_popup;


            //    dialog2.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog2.setContentView(R.layout.exhibitor_contact_layout);

            final LoginResponse loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));

            final EditText emailET = (EditText) dialog2.findViewById(R.id.emailET);
            final EditText nameET = (EditText) dialog2.findViewById(R.id.nameET);
            final EditText cmpnyET = (EditText) dialog2.findViewById(R.id.cmpnyET);
            final EditText purposeET = (EditText) dialog2.findViewById(R.id.purposeET);
            final EditText messageET = (EditText) dialog2.findViewById(R.id.messageET);
            if(loginResponse.getUser()!=null) {
                if (loginResponse.getUser().getUserName() != null) {
                    emailET.setText(loginResponse.getUser().getUserName());
                    emailET.setEnabled(false);
        /*    emailET.setFocusable(false);
            emailET.setClickable(false);*/
                    emailET.setBackgroundColor(context.getResources().getColor(R.color.lighter_gray));
                }
            }
            if(loginResponse.getCompany()!=null) {
                if (loginResponse.getCompany().getName() != null) {
                    cmpnyET.setText(loginResponse.getCompany().getName());
                    cmpnyET.setEnabled(false);
      /*      cmpnyET.setFocusable(false);
            cmpnyET.setClickable(false);*/
                    cmpnyET.setBackgroundColor(context.getResources().getColor(R.color.lighter_gray));

                }
            }
            Button sendButton = (Button) dialog2.findViewById(R.id.sendButton);
            ImageView cancelIV = (ImageView) dialog2.findViewById(R.id.cancelIV);
            cancelIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.dismiss();
                }
            });
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (nameET.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(context, context.getResources().getString(R.string.empty_name), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (emailET.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(context, context.getResources().getString(R.string.email_empty), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (cmpnyET.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(context, context.getResources().getString(R.string.empty_company), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (purposeET.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(context, context.getResources().getString(R.string.empty_purpose), Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (messageET.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(context, context.getResources().getString(R.string.empty_message), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(loginResponse.getCompany()!=null) {


                        ContactEmailRequestModel requestModel = new ContactEmailRequestModel();
                        requestModel.setSenderName(nameET.getText().toString());
                        requestModel.setSenderEmail(emailET.getText().toString());
                        requestModel.setSenderCompanyName(cmpnyET.getText().toString());
                        requestModel.setCommType("Send_Email");
                        requestModel.setPurpose(purposeET.getText().toString());

                        requestModel.setSenderCountry(loginResponse.getCompany().getCountry());
                        requestModel.setReceiverType(item.getCompanyId());
                        requestModel.setSenderType(AppConstant.EMAIL);
                        requestModel.setSenderId(loginResponse.getUser().getUserId());
                        requestModel.setReceiverId(item.getUniqueId());
                        requestModel.setMessage(messageET.getText().toString());

                        Log.d("Contact Email Request:", requestModel.serialize());

                        sendContactRequest(requestModel);
                    }else {
                        Toast.makeText(context,"There is no company id exist for this user for contact request",Toast.LENGTH_LONG).show();

                    }
                    // dialog2.dismiss();


                }
            });


            dialog2.show();
        }

        private void sendContactRequest(ContactEmailRequestModel request) {
            VolleyTaskListener taskListener = new VolleyTaskListener() {
                @Override
                public void postExecute(String response) {
                    contactEmailResponseModel = ContactEmailResponseModel.create(response);
                    if (contactEmailResponseModel.isStatus()) {
                    /*ProjectPrefrence.saveSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, loginResponse.serialize(), activity);

                    Intent intent=new Intent(activity,DashboardActivity.class);
                    startActivity(intent);*/
                        dialog2.dismiss();
                        Toast.makeText(context, "Contact request send successfully.", Toast.LENGTH_LONG).show();
                        return;
                    } else {

                        Toast.makeText(context, contactEmailResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    if (error != null) {
                        // String s = new String(error.networkResponse.data);
                        //  Log.d("ERROR MSG", s);
                        if (error instanceof TimeoutError) {
                            CustomAlert.alertWithOk(context, context.getResources().getString(R.string.timeout_issue));
                        } else if (AppUtilityFunction.isServerProblem(error)) {
                            // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                            CustomAlert.alertWithOk(context, context.getResources().getString(R.string.server_issue));
                        } else if (AppUtilityFunction.isNetworkProblem(error)) {
                            CustomAlert.alertWithOk(context, context.getResources().getString(R.string.IO_ERROR));
                        }
                    }
                }
            };
            volley = new CustomVolley(taskListener, "Please wait..", AppConstant.CONTACT_EMAIL, request.serialize(), null, null, context);
            volley.execute();
        }

        private void openViewProfileWindow(String aboutMe) {

            viewProfileDialog = new Dialog(context);
            // dialog = new Dialog(this, R.style.NewDialog);
            viewProfileDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            //  dialog.setCancelable(true);
            viewProfileDialog.setCanceledOnTouchOutside(true);
            //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
      /* WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
       lp.dimAmount = 0.6f;
       dialog2.getWindow().setAttributes(lp);*/
            viewProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            viewProfileDialog.getWindow().getAttributes().windowAnimations = R.style.animation_popup;


            //    dialog2.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            viewProfileDialog.setContentView(R.layout.create_exhibitor_profile);

            aboutMeET = (EditText) viewProfileDialog.findViewById(R.id.aboutMeET);
            aboutMeET.setBackground(null);
            aboutMeET.setText(aboutMe);
            aboutMeET.setEnabled(false);
            Button submitBTN = (Button) viewProfileDialog.findViewById(R.id.submitBTN);
            submitBTN.setVisibility(View.GONE);
            ImageView cancelIV = (ImageView) viewProfileDialog.findViewById(R.id.cancelIV);
            cancelIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewProfileDialog.dismiss();
                }
            });

            viewProfileDialog.show();
        }
        private void getProfile(CreateProfileRequestModel request) {
            VolleyTaskListener taskListener = new VolleyTaskListener() {
                @Override
                public void postExecute(String response) {
                    getProfileResponseModel = CreateProfileResponseModel.create(response);
                    if (getProfileResponseModel.isStatus()) {

                        if(getProfileResponseModel.getProfile()!=null
                                && getProfileResponseModel.getProfile().getAboutMe()!=null) {

                            openViewProfileWindow(getProfileResponseModel.getProfile().getAboutMe());

                        }
                        else
                        {
                            Toast.makeText(context, "There is no profile data", Toast.LENGTH_LONG).show();
                            return;
                        }

                    } else {

                        // Toast.makeText(context, getProfileResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "No profile data", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    if (error != null) {
                        // String s = new String(error.networkResponse.data);
                        //  Log.d("ERROR MSG", s);
                        if (error instanceof TimeoutError) {
                            CustomAlert.alertWithOk(context, context.getResources().getString(R.string.timeout_issue));
                        } else if (AppUtilityFunction.isServerProblem(error)) {
                            // Toast.makeText(getApplicationContext(),R.string.LOGIN_FAILED,Toast.LENGTH_LONG).show();

                            CustomAlert.alertWithOk(context, context.getResources().getString(R.string.server_issue));
                        } else if (AppUtilityFunction.isNetworkProblem(error)) {
                            CustomAlert.alertWithOk(context, context.getResources().getString(R.string.IO_ERROR));
                        }
                    }
                }
            };
            volley = new CustomVolley(taskListener, "Please wait..", AppConstant.GET_PROFILE, request.serialize(), null, null, context);
            volley.execute();
        }


    }
}
