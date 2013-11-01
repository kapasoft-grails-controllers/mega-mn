package com.minnehahalofts.app

class NotifierService {
    static transactional = false

    def mailService
    def customerServiceEmail = 'kmwaura@gmail.com'
    def applicationName = 'mega-mn'

    def informSubmission(inquiry, email, sub) {
        mailService.sendMail {
            to email
            subject sub
            body inquiry
        }
    }

    def contactUser(userEmail, title, content){
        mailService.sendMail {
            to userEmail
            subject title
            body content
        }
    }

    def registerRequest(objIns){
        switch(objIns.class.toString().split('\\.')[-1]){
            case 'Review':
                String message = 'Application name: ' + applicationName + ' \n' +
                        ' From: ' + objIns.submittedBy + '\n Rental Unit: ' + objIns.rentalUnit + '\n Content: ' + objIns.content
                informSubmission( message, customerServiceEmail, 'Review Received')
                break;
            case 'Inquiry':
                String message = 'Application name: ' + applicationName + ' \n' +
                        ' From: ' + objIns.userEmail + '\n Rental Unit: ' + objIns.rentalUnit + '\n Content: ' + objIns.inqContent
                informSubmission( message, customerServiceEmail, 'Inquiry Received')
                contactUser(objIns.userEmail,"Thank You For Inquiry", "Thank you. We received your inquiry and we will respond as soon as possible")
                break;
            case 'Contact':
                String message = 'Application name: ' + applicationName + ' \n From: ' + objIns.name + ' (' + objIns.email + ' and ' + objIns.phone + ')' + '\n ' + '\n Message: ' + objIns.message
                informSubmission( message, customerServiceEmail, 'ContactUs Received')
                break;
            case 'Newsletter':
                String message = 'Application name: ' + objIns?.hostname + ' \n Email subscribtion received with email: ' + objIns.email
                informSubmission( message, customerServiceEmail, 'Newsletter Subscription')
                break;
        }
    }
}
