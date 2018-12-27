package com.aeroindia.adapter;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.aeroindia.pojos.request.ExhibitorsItem;
import com.aeroindia.pojos.response.ContactEmailResponseModel;
import com.aeroindia.pojos.response.CreateProfileResponseModel;
import com.aeroindia.pojos.response.LoginResponse;
import com.aeroindia.view.activity.CreateExhibitorProfileActivity;
import com.aeroindia.view.activity.ExhibitorContactActivity;
import com.aeroindia.view.activity.FeedbackActivity;
import com.aeroindia.view.activity.HallRedirectionActivity;
import com.aeroindia.view.activity.StallLocationOnMapActivity;
import com.aeroindia.view.activity.UpdatesActivity;
import com.aeroindia.view.activity.ViewProfileActivity;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.customComponent.CustomAlert;
import com.customComponent.Networking.CustomVolley;
import com.customComponent.Networking.VolleyTaskListener;
import com.customComponent.utility.ProjectPrefrence;


import java.util.List;

/**
 * Created by PSQ on 11/5/2017.
 */

public class ExhibitorsAdapter extends RecyclerView.Adapter<ExhibitorsAdapter.MyViewHolder> {

    private List<ExhibitorsItem> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private static ClickListener clickListener;
    private String screenName;
    private double latt, longg;
    private Dialog dialog2,viewProfileDialog;
    private CustomVolley volley;
    private ContactEmailResponseModel contactEmailResponseModel;
    private EditText aboutMeET;
    private LoginResponse loginResponse;
    private CreateProfileResponseModel getProfileResponseModel;

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ExhibitorsAdapter.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView company_name_TV, address_name_TV;
        private LinearLayout button_layout, main_layout;
        private ImageView venuLoc;
        private RelativeLayout contactLayout,viewProfLayout,latestupdateLayout,locLayout,feedbackLayout;


        public MyViewHolder(View view) {
            super(view);
            company_name_TV = (TextView) view.findViewById(R.id.company_name_TV);
            address_name_TV = (TextView) view.findViewById(R.id.address_name_TV);
            button_layout = (LinearLayout) view.findViewById(R.id.button_layout);
            main_layout = (LinearLayout) view.findViewById(R.id.main_layout);
            contactLayout = (RelativeLayout) view.findViewById(R.id.contactLayout);
            viewProfLayout = (RelativeLayout) view.findViewById(R.id.viewProfLayout);
            latestupdateLayout=(RelativeLayout)view.findViewById(R.id.latestupdateLayout);
            feedbackLayout=(RelativeLayout)view.findViewById(R.id.feedbackLayout);
            locLayout=(RelativeLayout)view.findViewById(R.id.locLayout);
            venuLoc=(ImageView)view.findViewById(R.id.venuLoc);
        }


        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    public ExhibitorsAdapter(List<ExhibitorsItem> list, Context context, String screenName) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.screenName = screenName;
    }

    public ExhibitorsAdapter(List<ExhibitorsItem> list, Context context, String screenName, double latt, double longg) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.screenName = screenName;
        this.latt = latt;
        this.longg = longg;
        loginResponse = LoginResponse.create(ProjectPrefrence.getSharedPrefrenceData(AppConstant.PROJECT_PREF, AppConstant.LOGIN_DETAILS, context));

    }

    public ExhibitorsAdapter(List<ExhibitorsItem> list, Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exhibitor_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ExhibitorsItem item = list.get(position);
        holder.company_name_TV.setText(item.getName() + " , " + item.getCountry());
        if (item.getHallNo() != null && item.getStalNo() != null && !item.getHallNo().equalsIgnoreCase("N.A.")) {
            holder.venuLoc.setVisibility(View.VISIBLE);
            holder.address_name_TV.setText("#Hall - " + item.getHallNo() + " , " + "#Stall - " + item.getStalNo());
        } else {
            holder.venuLoc.setVisibility(View.GONE);

            holder.address_name_TV.setText("#Hall - " + "N.A." + " , " + "#Stall - " + "N.A.");

        }
        if (screenName != null && screenName.equalsIgnoreCase(AppConstant.B2BSCREEN)) {
            holder.button_layout.setVisibility(View.VISIBLE);
        }
if(loginResponse==null)
{
    holder.feedbackLayout.setVisibility(View.GONE);
    holder.contactLayout.setVisibility(View.GONE);
}
else
{
    holder.feedbackLayout.setVisibility(View.VISIBLE);
    holder.contactLayout.setVisibility(View.VISIBLE);
}
        holder.viewProfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ViewProfileActivity.class);
               // intent.putExtra("Screen","ExhibitorViewProfile");
                context.startActivity(intent);
               /* CreateProfileRequestModel getProfileRequest=new CreateProfileRequestModel();
                getProfileRequest.setExhibitorId(item.getCompanyId());
                Log.d("Get Profile Request",getProfileRequest.serialize());
                getProfile(getProfileRequest);*/
             //   openViewProfileWindow(item);
            }
        });

        holder.feedbackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,FeedbackActivity.class);
                intent.putExtra("companyId",item.getCompanyId());
                context.startActivity(intent);
            }
        });


        holder.contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ExhibitorContactActivity.class);
                intent.putExtra("ContactItem",item);
                context.startActivity(intent);

               // initiateNewUSerPopup(item);


            }
        });

            holder.locLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent=new Intent(context,StallLocationOnMapActivity.class);
                   context.startActivity(intent);
                /*  Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+latt+","+longg+"&daddr="+28.6126141+","+77.3633831));
                    context.startActivity(intent);*/
               }
            });

        holder.latestupdateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent=new Intent(context, UpdatesActivity.class);
                context.startActivity(_intent);
            }
        });
        holder.venuLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent=new Intent(context, HallRedirectionActivity.class);
                _intent.putExtra("hallname",item.getHallNo());
                context.startActivity(_intent);
            }
        });

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

                        Intent intent=new Intent(context, CreateExhibitorProfileActivity.class);
                       intent.putExtra("Screen","ExhibitorViewProfile");
                        context.startActivity(intent);

                        //openViewProfileWindow(getProfileResponseModel.getProfile().getAboutMe());

                    }
                    else
                    {
                        Toast.makeText(context, "There is no profile data", Toast.LENGTH_LONG).show();
                        return;
                    }

                } else {

                   // Toast.makeText(context, getProfileResponseModel.getErrorMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "There is no profile data", Toast.LENGTH_LONG).show();
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