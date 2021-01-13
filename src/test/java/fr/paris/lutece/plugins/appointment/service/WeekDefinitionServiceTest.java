/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.appointment.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.appointment.business.planning.WeekDefinition;
import fr.paris.lutece.plugins.appointment.business.rule.ReservationRule;
import fr.paris.lutece.plugins.appointment.business.rule.ReservationRuleHome;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentFormDTO;
import fr.paris.lutece.test.LuteceTestCase;

public class WeekDefinitionServiceTest extends LuteceTestCase
{

    /**
     * Find a week definition of a form and a date of apply
     */
    public void testFindWeekDefinitionByIdFormAndClosestToDateOfApply( )
    {
        // Build the form
        AppointmentFormDTO appointmentForm = FormServiceTest.buildAppointmentForm( );
        appointmentForm.setTimeEnd( "18:00" );
        int nIdForm = FormService.createAppointmentForm( appointmentForm );

        AppointmentFormDTO appointmentForm2 = FormServiceTest.buildAppointmentForm( );
        appointmentForm2.setIdForm( nIdForm );
        appointmentForm2.setTimeEnd( "20:00" );
        LocalDate dateOfModification = LocalDate.parse( "2028-06-20" );
        
        FormService.updateGlobalParameters( appointmentForm2 );
        LocalDate dateOfApply = LocalDate.parse( "2028-06-22" );
        appointmentForm2.setDateStartValidity( Date.valueOf( dateOfApply ) );
        FormService.updateForm( appointmentForm2 );
        WeekDefinition foundWeekDefinition = WeekDefinitionService.findWeekDefinitionByIdFormAndClosestToDateOfApply( nIdForm, dateOfApply );
        assertEquals( dateOfModification, foundWeekDefinition.getDateOfApply( ) );

        AppointmentFormDTO appointmentForm3 = FormServiceTest.buildAppointmentForm( );
        appointmentForm3.setIdForm( nIdForm );
        appointmentForm3.setTimeEnd( "19:00" );
        LocalDate dateOfModification2 = LocalDate.parse( "2028-06-21" );
        appointmentForm3.setDateOfModification( Date.valueOf( dateOfModification2 ) );
        FormService.updateForm( appointmentForm3 );

        foundWeekDefinition = WeekDefinitionService.findWeekDefinitionByIdFormAndClosestToDateOfApply( nIdForm, dateOfApply );
        assertEquals( dateOfModification2, foundWeekDefinition.getDateOfApply( ) );
        FormServiceTest.cleanForm( nIdForm );
    }


    /**
     * Return the min starting time of a list of week definitions
     */
    public void testGetMinStartingTimeOfAListOfWeekDefinition( )
    {
        // Build the form
    	List<ReservationRule> openDays = new ArrayList<>();
        AppointmentFormDTO appointmentForm = FormServiceTest.buildAppointmentForm( );
        appointmentForm.setTimeStart( "09:00" );
        int nIdForm = FormService.createAppointmentForm( appointmentForm );
        openDays.add( appointmentForm );

        AppointmentFormDTO appointmentForm2 = FormServiceTest.buildAppointmentForm( );
        appointmentForm2.setIdForm( nIdForm );
        appointmentForm2.setTimeStart( "10:00" );
        LocalDate dateOfModification = LocalDate.parse( "2028-06-20" );
        FormService.updateGlobalParameters( appointmentForm2 );
        openDays.add( appointmentForm2 );

        AppointmentFormDTO appointmentForm3 = FormServiceTest.buildAppointmentForm( );
        appointmentForm3.setIdForm( nIdForm );
        appointmentForm3.setTimeStart( "09:30" );
        LocalDate dateOfModification2 = LocalDate.parse( "2028-06-21" );
        FormService.updateGlobalParameters( appointmentForm3 );
        openDays.add( appointmentForm3 );

        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( nIdForm );
        assertEquals( LocalTime.parse( "09:00" ), WeekDefinitionService.getMinStartingTimeOfAListOfWeekDefinition( openDays ) );
        FormServiceTest.cleanForm( nIdForm );
    }

    /**
     * Return the min starting time of a week definition
     */
    public void testGetMinStartingTimeOfAWeekDefinition( )
    {
        // Build the form
        AppointmentFormDTO appointmentForm = FormServiceTest.buildAppointmentForm( );
        appointmentForm.setTimeStart( "09:00" );
        int nIdForm = FormService.createAppointmentForm( appointmentForm );
        WeekDefinition weekDefinition = WeekDefinitionService.findListWeekDefinition( nIdForm ).get( 0 );
        assertEquals( LocalTime.parse( "09:00" ), WeekDefinitionService.getMinStartingTimeOfAWeekDefinition( appointmentForm ) );
        FormServiceTest.cleanForm( nIdForm );
    }

    /**
     * Return the max ending time of a list of week definitions
     */
    public void testGetMaxEndingTimeOfAListOfWeekDefinition( )
    {

        // Build the form
    	List<ReservationRule> openDays = new ArrayList<>();
        AppointmentFormDTO appointmentForm = FormServiceTest.buildAppointmentForm( );
        appointmentForm.setTimeEnd( "18:00" );
        int nIdForm = FormService.createAppointmentForm( appointmentForm );
        openDays.add( appointmentForm );

        AppointmentFormDTO appointmentForm2 = FormServiceTest.buildAppointmentForm( );
        appointmentForm2.setTimeEnd( "19:00" );
        appointmentForm2.setIdForm( nIdForm );
        LocalDate dateOfModification = LocalDate.parse( "2028-06-20" );
        FormService.updateForm( appointmentForm2 );
        openDays.add( appointmentForm2 );

        AppointmentFormDTO appointmentForm3 = FormServiceTest.buildAppointmentForm( );
        appointmentForm3.setTimeEnd( "19:30" );
        appointmentForm3.setIdForm( nIdForm );
        LocalDate dateOfModification2 = LocalDate.parse( "2028-06-21" );
        FormService.updateForm( appointmentForm3 );
        openDays.add( appointmentForm3 );
        //openDays = ReservationRuleHome.findByIdForm( nIdForm);

        assertEquals( LocalTime.parse( "19:30" ), WeekDefinitionService.getMaxEndingTimeOfAListOfWeekDefinition( openDays ) );
        FormServiceTest.cleanForm( nIdForm );
    }

    /**
     * Get the max ending time of a week definition
     */
    public void testGetMaxEndingTimeOfAWeekDefinition( )
    {
        // Build the form
        AppointmentFormDTO appointmentForm = FormServiceTest.buildAppointmentForm( );
        appointmentForm.setTimeEnd( "19:00" );
        int nIdForm = FormService.createAppointmentForm( appointmentForm );
        WeekDefinition weekDefinition = WeekDefinitionService.findListWeekDefinition( nIdForm ).get( 0 );
        assertEquals( LocalTime.parse( "19:00" ), WeekDefinitionService.getMaxEndingTimeOfAWeekDefinition( appointmentForm ) );
        FormServiceTest.cleanForm( nIdForm );
    }

    /**
     * Get the min duration of a time slot of a week definition
     */
    public void testGetMinDurationTimeSlotOfAListOfWeekDefinition( )
    {
        // Build the form
    	List<ReservationRule> openDays = new ArrayList<>();
        AppointmentFormDTO appointmentForm = FormServiceTest.buildAppointmentForm( );
        appointmentForm.setName("appointment_form");
        appointmentForm.setDurationAppointments( 30 );
        int nIdForm = FormService.createAppointmentForm( appointmentForm );
        openDays.add( appointmentForm );

        AppointmentFormDTO appointmentForm2 = FormServiceTest.buildAppointmentForm( );
        appointmentForm2.setName("appointment_form");
        appointmentForm2.setIdForm( nIdForm );
        appointmentForm2.setDurationAppointments( 20 );
        LocalDate dateOfModification = LocalDate.parse( "2028-06-20" );
        FormService.updateGlobalParameters( appointmentForm2 );
        openDays.add( appointmentForm2 );

        AppointmentFormDTO appointmentForm3 = FormServiceTest.buildAppointmentForm( );
        appointmentForm3.setName("appointment_form");
        appointmentForm3.setIdForm( nIdForm );
        appointmentForm3.setDurationAppointments( 10 );
        LocalDate dateOfModification2 = LocalDate.parse( "2028-06-21" );
        FormService.updateGlobalParameters( appointmentForm3 );
        openDays.add( appointmentForm3 );

        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( nIdForm );

        assertEquals( 10, WeekDefinitionService.getMinDurationTimeSlotOfAListOfWeekDefinition( openDays ) );
        FormServiceTest.cleanForm( nIdForm );
    }

    /**
     * Get the set of the open days of all the week definitons
     */
    public void testGetOpenDaysOfWeek( )
    {
        // Build the form
        AppointmentFormDTO appointmentForm = FormServiceTest.buildAppointmentForm( );

        appointmentForm.setName("appointment_form");
        appointmentForm.setIsOpenMonday( Boolean.TRUE );
        appointmentForm.setIsOpenTuesday( Boolean.TRUE );
        appointmentForm.setIsOpenWednesday( Boolean.TRUE );
        appointmentForm.setIsOpenThursday( Boolean.TRUE );
        appointmentForm.setIsOpenFriday( Boolean.TRUE );
        appointmentForm.setIsOpenSaturday( Boolean.FALSE );
        appointmentForm.setIsOpenSunday( Boolean.FALSE );
        int nIdForm = FormService.createAppointmentForm( appointmentForm );
        

        List<WeekDefinition> listWeekDefinition = WeekDefinitionService.findListWeekDefinition( nIdForm );
        
        
        List<ReservationRule> openDays = new ArrayList<>();
        openDays.add( appointmentForm );

        assertEquals( 5, WeekDefinitionService.getOpenDaysOfWeek( openDays ) );
        FormServiceTest.cleanForm( nIdForm );
    }
}
